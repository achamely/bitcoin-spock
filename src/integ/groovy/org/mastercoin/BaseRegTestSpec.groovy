package org.mastercoin

import com.msgilligan.bitcoin.rpc.RPCURL
import org.mastercoin.rpc.MastercoinCLIClient
//import org.mastercoin.rpc.MastercoinClient
import org.mastercoin.rpc.MastercoinClientDelegate
import org.mastercoin.test.TestSupport
import spock.lang.Specification


/**
 * User: sean
 * Date: 7/26/14
 * Time: 7:01 PM
 */
class BaseRegTestSpec extends Specification implements MastercoinClientDelegate, TestSupport {

    static final BigDecimal minBTCForTests = 50.0;

//    @Shared
//    private BitcoinDaemon daemon

    {
        client = new MastercoinCLIClient(RPCURL.defaultRegTestURL, BaseMainNetSpec.rpcuser, BaseMainNetSpec.rpcpassword)
    }

    void setupSpec() {
//        daemon = new BitcoinDaemon("${executable} -printtoconsole -server -regtest -datadir=${dataDir}")
//        client = new MastercoinClient(RPCURL.defaultRegTestURL, rpcuser, rpcpassword)
        System.err.println("Waiting for server...")
        Boolean available = client.waitForServer(60)   // Wait up to 1 minute
        if (!available) {
            System.err.println("Timeout error.")
        }
        // Make sure we have enough test coins
        while (getBalance() < minBTCForTests) {
            // Mine blocks until we have some coins to spend
            client.generateBlocks(1)
        }
    }

    void cleanupSpec() {
//        daemon.stop()
    }
}