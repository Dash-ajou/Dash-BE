package io.saim.dash.account.push.repository;

import java.util.List;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.push.model.Push;

public interface PushRepository {

	List<Push> findByFilter(BooleanBuilder filter, int page, int size);

	Push findById(long pushId);
}
