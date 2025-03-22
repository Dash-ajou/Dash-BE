package io.saim.dash.global.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class PagingResponse<T> {
	private final int page;
	private final int size;
	private final int count;

	private final List<T> data;

	public PagingResponse(PageOptions pageOptions, List<T> data) {
		this.page = pageOptions.getPage();
		this.size = pageOptions.getSize();

		this.count = data.size();
		this.data = data;
	}

}
