package com.infinum.course.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http {
            cors{ }
            csrf{ disable() }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeRequests {
                authorize(HttpMethod.GET,"/car/{id}", hasRole("USER"), hasRole("ADMIN"))
                authorize(HttpMethod.POST,"/car", hasRole("USER") , hasRole("ADMIN"))
                authorize(HttpMethod.GET,"/car", hasRole("ADMIN"))
                authorize(HttpMethod.GET,"/carcheckup/{uuid}", hasRole("USER"), hasRole("ADMIN"))
                authorize(HttpMethod.POST,"/carcheckup", hasRole("ADMIN"))
                authorize(HttpMethod.DELETE, "/car", hasRole("ADMIN"))
                authorize(HttpMethod.DELETE, "/carcheckup", hasRole("ADMIN"))
                authorize("/stats", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                jwt {}
            }
        }
        return http.build()
    }


}