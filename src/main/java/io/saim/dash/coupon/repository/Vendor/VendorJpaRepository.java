package io.saim.dash.coupon.repository.Vendor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.model.VendorGroup;

public interface VendorJpaRepository extends JpaRepository<VendorGroup, Long> {

}
