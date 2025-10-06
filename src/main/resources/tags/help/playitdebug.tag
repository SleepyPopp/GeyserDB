type: text
aliases: playit.ggdebug, playitggdebug
title: :tools: playit.gg Debugging
color: help
image: https://geysermc.org/assets/images/added_tunnel-4f027ee6864df573d4d6c33deac14c42.png

---

The most common issue when setting up playit.gg is confusing the Geyser and playit.gg ports which are entirely separate. Below are ways to fix it:
- Option 1 (recommended): Delete or rename config.yml and restart, this resets all Geyser ports to their defaults. Also can fix config errors.
- Option 2: In Geyser config, change the bedrock port back to the default of 19132 and make sure that `clone-remote-port=false`.
- Option 3: Set `clone-remote-port=true` in Geyser config, then go to the playit.gg website on the same page as the image below, and where it says local port, put your java port and click update.

If you still have issues, make sure that you:
- Join with playit.gg's IP and port.
- Are using the playit.gg program agent and not the plugin.
- Have the program agent open while trying to join.
- Have followed the [playit.gg setup guide](https://geysermc.org/wiki/geyser/playit-gg/)'s instructions correctly.