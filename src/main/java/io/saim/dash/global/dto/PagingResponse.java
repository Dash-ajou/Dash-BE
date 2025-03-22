package io.saim.dash.global.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PagingResponse<T> {
	private final int page;
	private final int size;
	private final int count;

	private final List<T> data;

	public PagingResponse(int page, int size, List<T> data) {
		this.page = page;
		this.size = size;
		this.count = data.size();
		this.data = data;
	}
}
