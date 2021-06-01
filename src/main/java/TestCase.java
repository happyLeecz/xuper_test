import com.baidu.xuper.api.Account;
import com.baidu.xuper.api.Transaction;
import com.baidu.xuper.api.XuperClient;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class TestCase {
    public static void main(String[] as) {
        XuperClient client = new XuperClient("192.168.92.129:37101");
        Account account = Account.create("./keys");
        String abi = "[{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"constant\":true,\"inputs\":[{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"}],\"name\":\"get\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getOwner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"}],\"name\":\"increase\",\"outputs\":[],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"function\"}]\n";
        String bin = "608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506103d4806100606000396000f3fe6080604052600436106100345760003560e01c8063693ec85e14610039578063893d20e814610115578063ae896c871461016c575b600080fd5b34801561004557600080fd5b506100ff6004803603602081101561005c57600080fd5b810190808035906020019064010000000081111561007957600080fd5b82018360208201111561008b57600080fd5b803590602001918460018302840111640100000000831117156100ad57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610227565b6040518082815260200191505060405180910390f35b34801561012157600080fd5b5061012a61029a565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6102256004803603602081101561018257600080fd5b810190808035906020019064010000000081111561019f57600080fd5b8201836020820111156101b157600080fd5b803590602001918460018302840111640100000000831117156101d357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506102c3565b005b60006001826040518082805190602001908083835b6020831061025f578051825260208201915060208101905060208303925061023c565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b600180826040518082805190602001908083835b602083106102fa57805182526020820191506020810190506020830392506102d7565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054016001826040518082805190602001908083835b602083106103655780518252602082019150602081019050602083039250610342565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055505056fea265627a7a72315820c49614e9b0c57d73536ec4ffc169642345d796bb1e70a1f16a63746c6e165cad64736f6c63430005100032";

        Transaction t = client.deployEVMContract(account, bin.getBytes(), abi.getBytes(), "Counter", null);
        System.out.println("txID:" + t.getTxid());

        Map<String, String> args = new HashMap<>();
        args.put("key", "lili");
        // storagepay is a payable method. Amount param can be NULL if there is no need to transfer to the contract.
        Transaction t1 = client.invokeEVMContract(account, "Counter", "increase", args, BigInteger.ONE);
        System.out.println("txID:" + t1.getTxid());
        System.out.println("tx gas:" + t1.getGasUsed());

        Transaction t2 = client.queryEVMContract(account, "Counter", "get", args);
        System.out.println("tx res getMessage:" + t2.getContractResponse().getMessage());
        System.out.println("tx res getBodyStr:" + t2.getContractResponse().getBodyStr());

    }
}
