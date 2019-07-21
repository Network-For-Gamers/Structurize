package com.ldtteam.structurize;

import com.ldtteam.structurize.network.NetworkChannel;

public class Network
{
    /**
     * The network instance.
     */
    private static NetworkChannel network = new NetworkChannel("net-channel");

    /**
     * Get the network handler.
     * @return the network handler.
     */
    public static NetworkChannel getNetwork()
    {
        return network;
    }
}
