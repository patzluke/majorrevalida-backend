package org.ssglobal.training.codes.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyJwtTokenValidator extends OncePerRequestFilter {
	
//	@Autowired
//	private final UserTokenRepository userTokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//		String token = authorizationHeader.substring("Bearer".length()).trim();
//		response.sendError(HttpStatus.FORBIDDEN.value(), "not available");
//		if ((authorizationHeader == null 
//			 || !authorizationHeader.startsWith("Bearer")
//			) && !validateToken(token)) {
//			response.sendError(HttpStatus.UNAUTHORIZED.value());
//		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getRequestURI().matches("/api/subject/.*");
	}
	
//	@SuppressWarnings("unchecked")
//	private boolean validateToken(String token) {
//		try {
//			String[] chunks = token.split("\\.");
//			Decoder decoder = Base64.getUrlDecoder();			
//			String payload = new String(decoder.decode(chunks[1]));
//			HashMap<String,Object> result = new ObjectMapper().readValue(payload, HashMap.class);
//			
//			Date tokenExpiresAt = new Date(Long.parseLong(result.get("exp").toString()) * 1000L);
//			int userId = Integer.parseInt(result.get("userId").toString());
//			
//			if (new Date().getTime() < tokenExpiresAt.getTime() && userTokenRepository.isUserTokenExists(token)) {
//				return true;
//			} else if (new Date().getTime() > tokenExpiresAt.getTime()){
//				userTokenRepository.deleteUserToken(userId);
//			}
//		} catch (JsonMappingException e) {
//			log.debug("MyJwtTokenValidator Line 61 exception: %s".formatted(e.getMessage()));
//		} catch (JsonProcessingException e) {
//			log.debug("MyJwtTokenValidator Line 63 exception: %s".formatted(e.getMessage()));
//		} catch (ArrayIndexOutOfBoundsException e) {
//			log.debug("MyJwtTokenValidator Line 65 exception: %s".formatted(e.getMessage()));
//		} catch (Exception e) {
//			log.debug("MyJwtTokenValidator Line 67 exception: %s".formatted(e.getMessage()));
//		}
//		return false;
//	}
}
