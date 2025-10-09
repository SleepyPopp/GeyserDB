type: text
aliases: mpconnectionfailed, mpconnect, unabletoconnect, unabletoconnecttoworld
title: :x: Multiplayer Connection Failed
issues: Unable to connect to world, Multiplayer connection failed
color: errors

---

This means that the Bedrock client cannot reach the server. This is a network issue and typically caused by improper port forwarding, entering the wrong port in geyser config, or when joining.
Note that if `clone-remote-port=true` in geyser config, then your bedrock port will be ignored and will be the java port instead.
        └ - If you are using a hosting provider, run `/provider` followed by your host in https://discord.com/channels/613163671870242838/613194762249437245.
:small_orange_diamond: If your host is not in the list, run `!!networkdebug` instead.
- If you are self-hosting, we recommend using playit.gg which can be used to bypass port forwarding.
        └ - If you are already using playit.gg, run `!!playitdebug`
Additionally, there are various fixes for this on our wiki, which you can get to using the [Wiki page](https://wiki.geysermc.org/geyser/fixing-unable-to-connect-to-world/).
