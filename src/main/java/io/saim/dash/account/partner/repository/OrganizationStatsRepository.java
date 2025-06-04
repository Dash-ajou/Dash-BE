package io.saim.dash.account.partner.repository;

import io.saim.dash.coupon.common.model.Vendor;
import io.saim.dash.account.partner.dto.OrganizationRequestDetailProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrganizationStatsRepository extends CrudRepository<Vendor, Long> {
	@Query("""
    SELECT new io.saim.dash.account.partner.dto.OrganizationRequestDetailProjection(
        rp.product.productName,
        rp.quantity,
        rp.price,
        r.createdAt
    )
    FROM RequestProduct rp
    JOIN rp.request r
    WHERE r.vendor.vendorId = :vendorId
""")
	List<OrganizationRequestDetailProjection> findRequestDetailsByVendorId(Long vendorId);
}
