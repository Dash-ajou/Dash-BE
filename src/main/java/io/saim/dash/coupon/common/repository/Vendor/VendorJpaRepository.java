package io.saim.dash.coupon.common.repository.Vendor;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.VendorGroup;

public interface VendorJpaRepository extends JpaRepository<VendorGroup, Long> {

}
