package nxt;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

class UnconfirmedTransaction implements Transaction {

    private final TransactionImpl transaction;
    //private final long arrivalTimestamp;
    private final long feePerByte;

    UnconfirmedTransaction(TransactionImpl transaction) {
        this.transaction = transaction;
       // this.arrivalTimestamp = arrivalTimestamp;
        this.feePerByte = transaction.getFeeNQT() / transaction.getFullSize();
    }


    UnconfirmedTransaction(ResultSet rs) throws SQLException {
        byte[] transactionBytes = rs.getBytes("transaction_bytes");
        try {
            this.transaction = TransactionImpl.parseTransaction(transactionBytes);
            this.transaction.setHeight(rs.getInt("transaction_height"));
           // this.arrivalTimestamp = rs.getLong("arrival_timestamp");
            this.feePerByte = rs.getLong("fee_per_byte");
        } catch (NxtException.ValidationException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    void save(Connection con) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO unconfirmed_transaction (id, transaction_height, "
                + "fee_per_byte, expiration, transaction_bytes, height) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            int i = 0;
            pstmt.setLong(++i, transaction.getId());
            pstmt.setInt(++i, transaction.getHeight());
            pstmt.setLong(++i, feePerByte);
            pstmt.setInt(++i, transaction.getExpiration());
            pstmt.setBytes(++i, transaction.getBytes());
         //   pstmt.setLong(++i, arrivalTimestamp);
            pstmt.setInt(++i, Nxt.getBlockchain().getHeight());
            pstmt.executeUpdate();
        }
    }

    TransactionImpl getTransaction() {
        return transaction;
    }

   // long getArrivalTimestamp() {
    //    return arrivalTimestamp;
   // }

    long getFeePerByte() {
    return feePerByte;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UnconfirmedTransaction && transaction.equals(((UnconfirmedTransaction)o).getTransaction());
    }

    @Override
    public int hashCode() {
        return transaction.hashCode();
    }

    @Override
    public long getId() {
        return transaction.getId();
    }

    @Override
    public String getStringId() {
        return transaction.getStringId();
    }

    @Override
    public long getSenderId() {
        return transaction.getSenderId();
    }

    @Override
    public byte[] getSenderPublicKey() {
        return transaction.getSenderPublicKey();
    }

    @Override
    public long getRecipientId() {
        return transaction.getRecipientId();
    }

    @Override
    public int getHeight() {
        return transaction.getHeight();
    }

    @Override
    public long getBlockId() {
        return transaction.getBlockId();
    }

    @Override
    public Block getBlock() {
        return transaction.getBlock();
    }

    @Override
    public int getTimestamp() {
        return transaction.getTimestamp();
    }

    @Override
    public int getBlockTimestamp() {
        return transaction.getBlockTimestamp();
    }

    @Override
    public short getDeadline() {
        return transaction.getDeadline();
    }

    @Override
    public int getExpiration() {
        return transaction.getExpiration();
    }

    @Override
    public long getAmountNQT() {
        return transaction.getAmountNQT();
    }

    @Override
    public long getFeeNQT() {
        return transaction.getFeeNQT();
    }

    @Override
    public String getReferencedTransactionFullHash() {
        return transaction.getReferencedTransactionFullHash();
    }

    @Override
    public byte[] getSignature() {
        return transaction.getSignature();
    }

    @Override
    public String getFullHash() {
        return transaction.getFullHash();
    }

    @Override
    public TransactionType getType() {
        return transaction.getType();
    }

    @Override
    public Attachment getAttachment() {
        return transaction.getAttachment();
    }

    @Override
    public void sign(String secretPhrase) {

    }

    @Override
    public boolean verifyPublicKey() {
        return false;
    }

    @Override
    public boolean verifySignature() {
        return transaction.verifySignature();
    }

    @Override
    public void validate() throws NxtException.ValidationException {
        transaction.validate();
    }

    @Override
    public byte[] getBytes() {
        return transaction.getBytes();
    }

    @Override
    public byte[] getUnsignedBytes() {
        return transaction.getUnsignedBytes();
    }

    @Override
    public JSONObject getJSONObject() {
        return transaction.getJSONObject();
    }

    @Override
    public byte getVersion() {
        return transaction.getVersion();
    }

    @Override
    public Appendix.Message getMessage() {
        return transaction.getMessage();
    }


    @Override
    public Appendix.EncryptedMessage getEncryptedMessage() {
        return transaction.getEncryptedMessage();
    }


    public Appendix.EncryptToSelfMessage getEncryptToSelfMessage() {
        return transaction.getEncryptToSelfMessage();
    }


    @Override
    public List<? extends Appendix> getAppendages() {
        return transaction.getAppendages();
    }

    @Override
    public int getECBlockHeight() {
        return transaction.getECBlockHeight();
    }

    @Override
    public long getECBlockId() {
        return transaction.getECBlockId();
    }

    @Override
    public int compareTo(Transaction o) {
        return 0;
    }
}
