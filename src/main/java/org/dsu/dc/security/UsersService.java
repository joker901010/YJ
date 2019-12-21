package org.dsu.dc.security;

import org.dsu.dc.member.persistence.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Log
public class UsersService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public UsersService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return memberRepository.findById(username).filter(member -> member != null).map(member -> new SecurityUser(member)).get();

    }
}