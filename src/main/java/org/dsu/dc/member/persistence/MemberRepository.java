package org.dsu.dc.member.persistence;

import org.dsu.dc.member.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {

}