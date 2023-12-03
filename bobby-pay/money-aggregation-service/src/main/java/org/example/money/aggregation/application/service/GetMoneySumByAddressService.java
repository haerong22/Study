package org.example.money.aggregation.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.UseCase;
import org.example.money.aggregation.application.port.in.GetMoneySumByAddressCommand;
import org.example.money.aggregation.application.port.in.GetMoneySumByAddressUseCase;
import org.example.money.aggregation.application.port.out.GetMembershipPort;
import org.example.money.aggregation.application.port.out.GetMoneySumPort;
import org.example.money.aggregation.application.port.out.MemberMoney;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class GetMoneySumByAddressService implements GetMoneySumByAddressUseCase {

    private final GetMoneySumPort getMoneySumPort;
    private final GetMembershipPort getMembershipPort;

    @Override
    public int getMoneySumByAddress(GetMoneySumByAddressCommand command) {

        String targetAddress = command.getAddress();

        List<Long> membershipIds = getMembershipPort.getMembershipByAddress(targetAddress);

        List<List<Long>> membershipPartitionList = partitionList(membershipIds, 100);

        int sum = 0;
        for (List<Long> ids : membershipPartitionList) {
            sum += getMoneySumPort.getMoneySumByMembershipIds(ids)
                    .stream().mapToInt(MemberMoney::getBalance).sum();
        }

        return sum;
    }

    private List<List<Long>> partitionList(List<Long> list, int partitionSize) {
        if (list.size() <= partitionSize) {
            return List.of(list);
        }

        return IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.groupingBy(i -> i / partitionSize))
                .values()
                .stream()
                .map(i -> i.stream().map(list::get).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
