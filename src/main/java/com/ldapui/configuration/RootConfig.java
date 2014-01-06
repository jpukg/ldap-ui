package com.ldapui.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;

import com.novell.ldap.LDAPException;
import com.novell.ldap.connectionpool.PoolManager;

/**
 * The root configuration where most objects you want managed should go.
 */
@Configuration
@ComponentScan(basePackages = { "com.ldapui.service", "com.ldapui.connection" })
@PropertySource("classpath:/com/ldapui/configuration/ldap/ldap-connection.properties")
public class RootConfig {

	/**
	 * Must be declared so that the properties file can be picked up and used in combination with
	 * the @Value annotation.
	 * 
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${host}")
	String host;
	@Value("${port}")
	int port;
	@Value("${pool.max.cons}")
	int connectionPoolMaxCons;
	@Value("${pool.max.shared.cons}")
	int connectionPoolMaxSharedCons;

	/**
	 * Manages connections to the LDAP data source.
	 * 
	 * @return {@link PoolManager}
	 * @throws LDAPException
	 */
	@Bean
	public PoolManager poolManager() throws LDAPException {
		return new PoolManager(host, port, connectionPoolMaxCons, connectionPoolMaxSharedCons, null);
	}

	/**
	 * LDAP password encoder.
	 * 
	 * @return an instance of LdapShaPasswordEncoder
	 */
	@Bean
	public LdapShaPasswordEncoder ldapShaPasswordEncoder() {
		return new LdapShaPasswordEncoder();
	}
}