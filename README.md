#Spring Security – Roles y Permisos

Proyecto de práctica creado para aprender y probar conceptos fundamentales de Spring Security, 
incluyendo autenticación, autorización, manejo de usuarios, roles y permisos.

#Objetivo del proyecto

Servir como base para experimentar y comprender cómo funciona Spring Security con modelos de roles y permisos personalizados,
ideal para proyectos más grandes o APIs reales.

Actualmente sin bases de datos lo unico a configurar son las variables de entorno de cada usuario 

spring.security.user.name=${SS_USER}         
spring.security.user.password=${SS_PASSWORD}

Cambiar por tu user y por tu contraseña

Como esto es sin base de datos los usuarios y roles creados se guardan en memoria 
#Paso a dejarte el SecurityConfig para que puedas analizarlo

@Configuration
@EnableWebSecurity
public class SecurityConfig {


   

        @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
            // Endpoints públicos
            http.requestMatchers(HttpMethod.GET, "/holanoseg").permitAll();
            http.requestMatchers(HttpMethod.GET, "/holaseg").hasAuthority("READ");
            http.anyRequest().denyAll();
        })
            .build();
    }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
            return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder());
            provider.setUserDetailsService(userDetailsService());
            return provider;
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return NoOpPasswordEncoder.getInstance(); // Contraseña no encriptada 
        }

        @Bean
        public UserDetailsService userDetailsService(){
            List userdetailsList = new ArrayList<>();  // Usuarios logicos guardados en memoria para ser pasados al authenticationProvider

            userdetailsList.add(User.withUsername("sara")
                .password("12345")
                .roles("ADMIN")
                .authorities("CREATE","READ","UPDATE","DELETE")
                .build());

            userdetailsList.add(User.withUsername("carlo")
                .password("12345")
                .roles("USER")
                .authorities("READ")
                .build());

            userdetailsList.add(User.withUsername("pablo")
                .password("12345")
                .roles("USER")
                .authorities("UPDATE")
                .build());

            // InMemoryUserDetailsManager es una clase de Spring Security que almacena usuarios en memoria,
            // es decir, no usa base de datos.
            // Solo existe mientras la aplicación está corriendo.
            return new InMemoryUserDetailsManager(userdetailsList); 
            



        }


}

##Cómo ejecutar

#Cloná el repo

git clone https://github.com/Garcetejulian3/prueba_security_roles_permisos.git


##Ejecutá la aplicación desde tu IDE o con Maven:

mvn spring-boot:run

##Objetivo del proyecto

Servir como base para experimentar y comprender cómo funciona Spring Security con modelos de roles y permisos personalizados,
ideal para proyectos más grandes o APIs reales.
