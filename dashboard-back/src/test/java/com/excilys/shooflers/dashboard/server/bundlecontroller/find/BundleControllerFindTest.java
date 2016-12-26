package com.excilys.shooflers.dashboard.server.bundlecontroller.find;

import com.excilys.shooflers.dashboard.server.bundlecontroller.AbstractBundleControllerTest;
import com.excilys.shooflers.dashboard.server.dao.BundleDao;
import com.excilys.shooflers.dashboard.server.dto.BundleMetadataDto;
import com.excilys.shooflers.dashboard.server.model.metadata.BundleMetadata;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Camille Vrod on 15/06/16.
 */
public class BundleControllerFindTest extends AbstractBundleControllerTest {

    @Test
    @Ignore("Change not yet reported")
    public void bundleAll() throws Exception {
        mockMvc.perform(getAuthenticated("/bundle"))
                .andExpect(status().isOk());

//        fromJson(result.getResponse().getContentAsString(), );
    }

    /**
     * The main goal is to check that an empty list doesn't generate an error
     */
    @Test
    @Ignore("Change not yet reported")
    public void bundleAllEmpty() throws Exception {
        File bundleFolder = new File(props.getBasePath() + "/" + BundleDao.ENTITY_NAME);

        if (bundleFolder.exists()) {
            FileUtils.deleteDirectory(bundleFolder);
            assertFalse(bundleFolder.exists());
        }
        assertTrue(bundleFolder.mkdir());

        mockMvc.perform(getAuthenticated("/bundle"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void bundleNotFoundNegative() throws Exception {
        mockMvc.perform(getAuthenticated("/bundle/dsfsdf"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void bundleNotFoundReally() throws Exception {
        mockMvc.perform(getAuthenticated("/bundle/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void bundleFound() throws Exception {
        final String name = "bundleMetadataDto";
        BundleMetadata bundleMetadata = new BundleMetadata.Builder().name(name).build();
        bundleService.save(bundleMetadata);

        BundleMetadataDto bundleMetadataDto = bundleDtoMapper.toDto(bundleMetadata);

        MvcResult result = mockMvc.perform(getAuthenticated(("/bundle/" + bundleMetadataDto.getTag())))
                .andExpect(status().isOk())
                .andReturn();

        bundleMetadata = fromJson(result.getResponse().getContentAsString(), BundleMetadata.class);
        assertEquals(name, bundleMetadata.getName());
        assertNull(bundleMetadata.getValidity());
    }
}