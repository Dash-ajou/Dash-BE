package io.saim.dash.coupon.common.repository.DUMMY;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DUMMY_GeneralUserRepositoryImpl implements DUMMY_GeneralUserRepository {
}

interface DUMMY_GeneralUserJPARepository extends JpaRepository<DUMMY_GeneralUser, Long> {}
