package com.excilys.shooflers.dashboard.server.dao.impl;

import com.excilys.shooflers.dashboard.server.dao.BundleDao;
import com.excilys.shooflers.dashboard.server.dao.util.BundleReverseIndex;
import com.excilys.shooflers.dashboard.server.dao.util.YamlUtils;
import com.excilys.shooflers.dashboard.server.exception.ResourceIoException;
import com.excilys.shooflers.dashboard.server.model.metadata.BundleMetadata;
import com.excilys.shooflers.dashboard.server.property.DashboardProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author Loïc Ortola on 07/06/2016.
 */
@Component
@DependsOn("daoInitializer")
public class BundleDaoImpl implements BundleDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(BundleDaoImpl.class);

    @Autowired
    private DashboardProperties props;

    @Autowired
    private FileSystem fileSystem;

    private BundleReverseIndex bri = new BundleReverseIndex();

    private Path bundleDatabasePath;

    @PostConstruct
    public void init() {
        bundleDatabasePath = fileSystem.getPath(props.getBasePath(), ENTITY_NAME);
        bri.refreshDataset(getAll());
    }

    @Override
    public BundleMetadata get(String uuid) {
        Path dataFile = getBundleFile(uuid);
        return readBundleFromFile(dataFile);
    }

    @Override
    public BundleMetadata getByTag(String tag) {
        String uuid = bri.getBundleUuid(tag);
        Path dataFile = getBundleFile(uuid);
        return readBundleFromFile(dataFile);
    }

    @Override
    public List<BundleMetadata> getAll() {
        List<BundleMetadata> bundles = new LinkedList<>();
        try {
            Files.walk(bundleDatabasePath, 1)
                    .filter(Files::isRegularFile)
                    .forEach(path -> bundles.add(readBundleFromFile(path)));
        } catch (IOException e) {
            LOGGER.error("exception in BundleDaoImpl#getAll", e);
            throw new ResourceIoException(e);
        }
        return bundles;
    }

    @Override
    public BundleMetadata save(BundleMetadata bundle) {
        if (bundle.getUuid() == null) {
            bundle.setUuid(UUID.randomUUID().toString());
        }
        Path dest = getBundleFile(bundle.getUuid());
        YamlUtils.store(bundle, dest);
        refreshReverseIndex();
        //bri.addEntry(bundle.getTag(), bundle.getUuid());
        return bundle;
    }

    @Override
    public BundleMetadata delete(String uuid) {
        BundleMetadata result = get(uuid);
        boolean success = YamlUtils.delete(bundleDatabasePath.resolve(uuid + ".yaml").toFile());
        if (!success) {
            throw new ResourceIoException();
        }
        refreshReverseIndex();
        return result;
    }

    private Path getBundleFile(String uuid) {
        String dataFileName = uuid + ".yaml";
        return bundleDatabasePath.resolve(dataFileName);
    }


    private BundleMetadata readBundleFromFile(Path path) {
        return YamlUtils.read(path, BundleMetadata.class);
    }


    private void refreshReverseIndex() {
        bri.invalidate();
        bri.refreshDataset(getAll());
    }
}
