package ru.gb.web_market.order.security;

//@Component
//public class StandardAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    public void setUserDetailsService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        if(!Objects.equals(userDetails.getPassword(), authentication.getCredentials())){
//            throw new BadCredentialsException("Пароль неверен.");
//        }
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication){
//        try {
//            return userDetailsService.loadUserByUsername(username);
//        } catch (AuthenticationException e){
//            throw new BadCredentialsException("Пользователь не найден.");
//        }
//    }
//}
