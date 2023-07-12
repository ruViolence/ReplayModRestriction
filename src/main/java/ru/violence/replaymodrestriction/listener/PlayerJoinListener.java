package ru.violence.replaymodrestriction.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final ClientboundCustomPayloadPacket packet;

    public PlayerJoinListener() {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        // ReplayMod doesn't have any settings yet. This packet breaks the recording completely.
        output.writeUTF("");
        output.writeBoolean(false);
        this.packet = new ClientboundCustomPayloadPacket(
                new ResourceLocation("replaymod", "restrict"),
                new FriendlyByteBuf(Unpooled.wrappedBuffer(output.toByteArray()))
        );
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("replaymodrestriction.bypass")) {
            ((CraftPlayer) player).getHandle().connection.send(packet);
        }
    }
}
