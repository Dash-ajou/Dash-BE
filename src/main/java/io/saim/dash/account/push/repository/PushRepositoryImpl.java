package io.saim.dash.account.push.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.account.push.model.Push;
import io.saim.dash.account.push.model.QPush;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor

public class PushRepositoryImpl implements PushRepository {

	private final PushJpaRepository jpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Push> findByFilter(BooleanBuilder filter, int page, int size) {
		QPush push = QPush.push;
		return queryFactory.selectFrom(push)
			.where(filter)
			.offset((page-1) * size)
			.limit(size)
			.fetch();
	}

	@Override
	public Push findById(long pushId) {
		return jpaRepository.findById(pushId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.ISSUE_NOT_FOUND));
	}
}
