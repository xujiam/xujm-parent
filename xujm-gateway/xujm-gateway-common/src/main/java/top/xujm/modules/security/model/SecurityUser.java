package top.xujm.modules.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUser;

import java.util.Collection;


/**
 * @author Xujm
 */
public class SecurityUser extends SocialUser implements UserDetails {

    private static final long serialVersionUID = 1415046458869659218L;

    private String userId;

    private String loginCode;

    private String nickname;

    private String avatar;

    public SecurityUser(String username, String password, String userId, String loginCode, String nickname, String avatar, boolean enable, Collection<? extends GrantedAuthority> authorities){

        super(username,password,enable,true,true,true,authorities);
        this.userId = userId;
        this.loginCode = loginCode;
        this.nickname = nickname;
        this.avatar = avatar;
    }


    @Override
    public String getUserId() {
        return userId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public String getNickname() {
        return nickname;
    }


    public String getAvatar() {
        return avatar;
    }

}
