package io.saim.dash.account.general.dto;

import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralAccountResponseDTO extends CommonResponseDTO<GeneralAccountResponseDTO.Data> {

	public GeneralAccountResponseDTO(String apiVersion, String clientVersion, APIStatus status, String message, Data data) {
		super(new VersionResponseDTO(apiVersion, clientVersion), status, message, data);
	}

	@Override
	public GeneralAccountResponseDTO.Data getData() {
		return super.getData();
	}

	@Getter
	@Setter
	public static class Data {
		private String generalName;
		private String generalEmail;
		private String generalPhone;

		public Data(String generalName, String generalEmail, String generalPhone) {
			this.generalName = generalName;
			this.generalEmail = generalEmail;
			this.generalPhone = generalPhone;
		}

		@Override
		public String toString() {
			return "Data{" +
				"generalName='" + generalName + '\'' +
				", generalEmail='" + generalEmail + '\'' +
				", generalPhone='" + generalPhone + '\'' +
				'}';
		}
	}
}
