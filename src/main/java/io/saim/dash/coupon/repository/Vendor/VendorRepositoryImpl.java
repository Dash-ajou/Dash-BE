package io.saim.dash.coupon.repository.Vendor;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.model.VendorGroup;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VendorRepositoryImpl implements VendorRepository {

	private final VendorJpaRepository vendorJpaRepository;

	@Override
	public void save(VendorGroup vendorGroup) {
		vendorJpaRepository.save(vendorGroup);
	}
}
