package edu.tongji.webgis.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.tongji.webgis.model.User;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@JsonIgnoreProperties(value = { "authorities" }, ignoreUnknown = true)
public class AccountUserDetails implements UserDetails, CredentialsContainer {

	private static final long serialVersionUID = 2745987116881538294L;

	private User account;
	// private long id;
	// private String username;
	private long expires;

	private ArrayList<SimpleGrantedAuthority> grantedAuths;

	// private String password;

	public AccountUserDetails() {
	}

	public AccountUserDetails(User account) {
		this.account = account;
		// this.id = account.getId();
		// this.username = account.getNumber();
		// this.password = account.getPassword();
		// this.expires = account.getExpires();
		this.grantedAuths = new ArrayList<SimpleGrantedAuthority>(1);
		this.grantedAuths.add(new SimpleGrantedAuthority(account.getRole()
				.toRoleString()));
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.grantedAuths;
	}

	public ArrayList<String> getGrantedAuths() {
		ArrayList<String> l = new ArrayList<String>(1);
		for (SimpleGrantedAuthority auth : grantedAuths) {
			l.add(auth.getAuthority());
		}
		return l;
	}

	public void setGrantedAuths(ArrayList<String> authStrs) {
		this.grantedAuths = new ArrayList<SimpleGrantedAuthority>(1);
		for (String authStr : authStrs) {
			this.grantedAuths.add(new SimpleGrantedAuthority(authStr));
		}
	}

	public String getPassword() {
		return this.account.getPassword();
	}

	public String getUsername() {
		return this.account.getUsername();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public void eraseCredentials() {
		this.account.setPassword(null);
	}

	public User getAccount() {
		return account;
	}

	public void setAccount(User account) {
		this.account = account;
	}

	public long getExpires() {
		return this.expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}
	//
	// public long getId() {
	// return id;
	// }
	//
	// public void setId(long id) {
	// this.id = id;
	// }
}
