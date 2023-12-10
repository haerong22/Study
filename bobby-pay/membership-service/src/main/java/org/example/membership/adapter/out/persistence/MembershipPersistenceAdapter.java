package org.example.membership.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistenceAdapter;
import org.example.membership.adapter.out.vault.VaultAdapter;
import org.example.membership.application.port.out.FindMembershipPort;
import org.example.membership.application.port.out.ModifyMembershipPort;
import org.example.membership.application.port.out.RegisterMembershipPort;
import org.example.membership.domain.Membership;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;
    private final VaultAdapter vaultAdapter;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp, Membership.RefreshToken refreshToken) {

        String encryptedEmail = vaultAdapter.encrypt(membershipEmail.getMembershipEmail());

        MembershipJpaEntity saved = membershipRepository.save(
                new MembershipJpaEntity(
                        membershipName.getMembershipName(),
                        encryptedEmail,
                        membershipAddress.getMembershipAddress(),
                        membershipIsValid.isMembershipIsValid(),
                        membershipIsCorp.isMembershipIsCorp(),
                        refreshToken.getRefreshToken()
                )
        );

        MembershipJpaEntity clone = saved.clone();
        clone.setEmail(membershipEmail.getMembershipEmail());
        return clone;
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        MembershipJpaEntity clone = entity.clone();
        clone.setEmail(vaultAdapter.decrypt(entity.getEmail()));
        return entity;
    }

    @Override
    public List<MembershipJpaEntity> findMembershipListByAddress(Membership.MembershipAddress membershipAddress) {
        String address = membershipAddress.getMembershipAddress();
        List<MembershipJpaEntity> entity = membershipRepository.findByAddress(address);
        return entity.stream().map(e -> {
            MembershipJpaEntity clone = e.clone();
            clone.setEmail(vaultAdapter.decrypt(e.getEmail()));
            return clone;
        }).collect(Collectors.toList());
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp, Membership.RefreshToken refreshToken) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));

        String encryptedEmail = vaultAdapter.encrypt(membershipEmail.getMembershipEmail());

        entity.setName(membershipName.getMembershipName());
        entity.setAddress(membershipAddress.getMembershipAddress());
        entity.setEmail(encryptedEmail);
        entity.setCorp(membershipIsCorp.isMembershipIsCorp());
        entity.setValid(membershipIsValid.isMembershipIsValid());
        entity.setRefreshToken(refreshToken.getRefreshToken());

        MembershipJpaEntity saved = membershipRepository.save(entity);
        MembershipJpaEntity clone = saved.clone();
        clone.setEmail(membershipEmail.getMembershipEmail());
        return clone;
    }
}
