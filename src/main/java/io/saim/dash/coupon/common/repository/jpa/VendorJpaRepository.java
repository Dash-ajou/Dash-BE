package io.saim.dash.coupon.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.Vendor;

public interface VendorJpaRepository extends JpaRepository<Vendor, Long> {

}
