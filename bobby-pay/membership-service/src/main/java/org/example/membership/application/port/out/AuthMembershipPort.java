
package org.example.membership.application.port.out;

public interface AuthMembershipPort {

	String generateAccessToken(Long membershipId);

	String generateRefreshToken(Long membershipId);

	boolean validateToken(String token);

	Long parseMembershipIdFromToken(String token);
}