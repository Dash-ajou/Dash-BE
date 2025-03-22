package io.saim.dash.global.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PageOptions {
	private int page = 0;
	private int size;
}
