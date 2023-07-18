package choptree.choptree.command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

public class CommandKit implements CommandExecutor, Listener {

    private static boolean pluginEnabled;
    private static Player player;

    private static final List<Material> TREE_MATERIALS = Arrays.asList(
            Material.SPRUCE_LOG,
            Material.OAK_LOG,
            Material.BIRCH_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.DARK_OAK_LOG,
            Material.MANGROVE_LOG
    );

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        player = (Player) sender;
        if (sender instanceof Player) {
            pluginEnabled = true;
            player.sendMessage("Chop Tree enabled!");
        } else {
            pluginEnabled = false;
            sender.sendMessage("Chop Tree disabled!");
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteractEventTree(PlayerInteractEvent e) {
        if (pluginEnabled && e.getAction() == Action.LEFT_CLICK_BLOCK && player.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE) {
            Block b = e.getClickedBlock();
            if (b != null && checkTreeType(b.getType())) {
                breakConnectedWoodBlocks(b);
            }
        }
    }

    public void breakConnectedWoodBlocks(Block b) {
        if (checkTreeType(b.getType())) {
            b.breakNaturally();
            for (BlockFace blockFace : BlockFace.values()) {
                Block adjacentBlock = b.getRelative(blockFace);
                if (adjacentBlock != b && checkTreeType(adjacentBlock.getType())) {
                    breakConnectedWoodBlocks(adjacentBlock);
                }
            }
        }
    }

    private boolean checkTreeType(Material material) {
        return TREE_MATERIALS.contains(material);
    }
}