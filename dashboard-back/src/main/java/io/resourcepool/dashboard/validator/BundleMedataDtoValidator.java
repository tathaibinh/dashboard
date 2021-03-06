package io.resourcepool.dashboard.validator;

import io.resourcepool.dashboard.dto.BundleMetadataDto;
import io.resourcepool.dashboard.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validator of an {@link BundleMetadataDto}
 *
 * @author Mickael
 */
@Component
public class BundleMedataDtoValidator implements Validator<BundleMetadataDto> {

    @Autowired
    private ValidityDtoValidatorImpl validityDtoValidator;

    @Override
    public void validate(BundleMetadataDto bundleMetadataDto) {
        if (bundleMetadataDto == null) {
            throw new ValidationException("Bundle can't be null");
        }

        if (bundleMetadataDto.getName() == null || bundleMetadataDto.getName().isEmpty()) {
            throw new ValidationException("Bundle Name can't be empty");
        }

        if (bundleMetadataDto.getValidity() != null) {
            validityDtoValidator.validate(bundleMetadataDto.getValidity());
        }
    }
}
