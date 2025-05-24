package io.saim.dash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan(basePackages = {
	"io.saim.dash.account.auth.model",
	"io.saim.dash.account.general.model",
	"io.saim.dash.account.partner.model",
	"io.saim.dash.account.common.model",
	"io.saim.dash.coupon.common.model",
	"io.saim.dash.account.general.coupon.model"
})
@EnableJpaRepositories(basePackages = {
	"io.saim.dash.account.auth.repository",
	"io.saim.dash.account.general.repository",
	"io.saim.dash.account.partner.repository",
	"io.saim.dash.account.general.coupon.repository",
	"io.saim.dash.coupon.common.repository"
})

public class DashApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashApplication.class, args);
	}
}
