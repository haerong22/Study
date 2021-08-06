package core;

import util.Utils;

import java.security.PublicKey;
import java.sql.Timestamp;

public class Transaction {
    String signature;
    PublicKey sender;
    PublicKey receiver;
    double amount;
    Timestamp timestamp;

    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public PublicKey getSender() {
        return sender;
    }
    public void setSender(PublicKey sender) {
        this.sender = sender;
    }
    public PublicKey getReceiver() {
        return receiver;
    }
    public void setReceiver(PublicKey receiver) {
        this.receiver = receiver;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    // 어떠한 지갑 주소로 얼마만큼의 가상화폐를 보냈는지에 대한 정보를 기준으로 초기화
    public Transaction(Wallet wallet, PublicKey receiver, double amount, String timestamp) throws Exception {
        this.sender = wallet.getPublicKey();
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = java.sql.Timestamp.valueOf(timestamp);
        this.signature = wallet.sign(getData());
    }

    // 서명 값을 포함한 트랜잭션 정보를 반환
    public String getInformation() {
        return "<" + signature + ">\n" +
                new Utils().getHash(sender.toString()) + " -> " +
                new Utils().getHash(receiver.toString()) + " : " +
                amount + "개 (" + timestamp + ")";
    }

    // 서명 값을 제외한 단순 트랜잭션 정보를 반환
    public String getData() {
        return new Utils().getHash(sender.toString()) + " -> " +
                new Utils().getHash(receiver.toString()) + " : " +
                amount + "개 (" + timestamp + ")";
    }
}
