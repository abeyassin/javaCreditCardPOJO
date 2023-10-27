import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



// Define a Student class
class CreditCard {
    private String name;
    private long creditnumber;
    private int cvv;
    private String expdate;



    public CreditCard(String name, long creditnumber, int cvv, String expdate) {
        this.name = name;
        this.creditnumber = creditnumber;
        this.cvv = cvv;
        this.expdate = expdate;
    }



    // Getters for student properties
    public String getName() {
        return name;
    }



    public long getCreditnumber() {
        return creditnumber;
    }



    public int getCvv() {
        return cvv;
    }

    public String getExpdate() {
        return expdate;

        }
}




// Define a Block class
class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private CreditCard creditCard;



    public Block(int index, String previousHash, CreditCard creditCard) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.creditCard = creditCard;
        this.hash = calculateHash();
    }



    // Calculate the hash of the block
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            int nonce = 0;
            String input;

            while (true) {
                input = index + timestamp + previousHash + creditCard.getName() + creditCard.getCreditnumber() + creditCard.getCvv() + creditCard.getExpdate() + nonce;
                byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                String hash = hexString.toString();

                // Check if the hash starts with "00"
                if (hash.startsWith("192")) {
                    return hash;
                }

                // If not, increment the nonce and try again
                nonce++;
            }
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



    // Getters
    public int getIndex() {
        return index;
    }



    public long getTimestamp() {
        return timestamp;
    }



    public String getPreviousHash() {
        return previousHash;
    }



    public String getHash() {
        return hash;
    }



    public CreditCard getCreditCard() {
        return creditCard;
    }
}



// Define a Blockchain class
class Blockchain {
    private List<Block> chain;



    // Constructor
    public Blockchain() {
        chain = new ArrayList<Block>();
        // Create the genesis block (the first block in the chain)
        chain.add(new Block(0, "0", new CreditCard("Genesis Block", 0000000000000000, 0, "0/00")));
    }



    // Add a new block to the blockchain
    public void addCreditcard(CreditCard creditCard) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getIndex() + 1, previousBlock.getHash(), creditCard);
        chain.add(newBlock);
    }



    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Name: " + block.getCreditCard().getName());
            System.out.println("Credit Card Number: " + block.getCreditCard().getCreditnumber());
            System.out.println("CVV: " + block.getCreditCard().getCvv());
            System.out.println("Expiration Date: " + block.getCreditCard().getExpdate());
            System.out.println();
        }
    }
}



public class SimpleBlockchain {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        // DatabaseManager dbManager = new DatabaseManager();

        // Create student objects and add them to the blockchain
        CreditCard creditCard1 = new CreditCard("Joe Oakes", 1236547825984785l, 118, "11/25");
        CreditCard creditCard2 = new CreditCard("Branden Vasquez", 7859632145201458l, 998, "07/21");
        CreditCard creditCard3 = new CreditCard("Su Gon Deez Jr.", 9685446844687654l, 885, "06/24");



        blockchain.addCreditcard(creditCard1);
        blockchain.addCreditcard(creditCard2);
        blockchain.addCreditcard(creditCard3);



        // Print the blockchain
        blockchain.printBlockchain();
    }
}