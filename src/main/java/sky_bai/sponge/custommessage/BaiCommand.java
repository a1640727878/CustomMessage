package sky_bai.sponge.custommessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class BaiCommand implements CommandCallable{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		if (arguments.startsWith("reload")) {
			CustomMessage.setConfig();
			source.sendMessage(Text.of("§l[CustomMessage]§r插件重载完成"));
			return CommandResult.success();
		} else if (arguments.startsWith("send ")) {
			String argmane = arguments.replace("send ", "");
			if (!BaiConfig.CMCSet.contains(argmane)) {
				return CommandResult.empty();
			}
			Map<String, Object> a1 = BaiConfig.CMConfig.get(argmane);
			PaginationList.Builder a2  = PaginationList.builder().title(Text.of(a1.get("Title"))).linesPerPage((int) a1.get("Page") +2).padding(Text.of(a1.get("Padding")));
			List<Text> texts = new ArrayList<Text>();
			Set<String> a3 = new HashSet<>((Collection)a1.get("Contents"));
			for (String s : a3) {
				texts.add(TextSerializers.JSON.deserializeUnchecked(s));
			}
			a2.contents(texts).sendTo(source);
			return CommandResult.success();
		}else if (arguments.startsWith("sendto ")) {
			String playername =  arguments.replace("sendto ", "").replaceFirst(" .*","");
			Player player = null;
			if (Sponge.getServer().getPlayer(playername).isPresent()) {
				player = Sponge.getServer().getPlayer(playername).get();
			}
			if (player == null) {
				source.sendMessage(Text.of("§l[CustomMessage]§r玩家不存在或不在线"));
				return CommandResult.empty();
			}
			String argmane = arguments.replace("sendto ", "").replace(playername+ " ","");
			Map<String, Object> a1 = BaiConfig.CMConfig.get(argmane);
			PaginationList.Builder a2  = PaginationList.builder().title(Text.of(a1.get("Title"))).linesPerPage((int) a1.get("Page") +2).padding(Text.of(a1.get("Padding")));
			List<Text> texts = new ArrayList<Text>();
			Set<String> a3 = new HashSet<>((Collection)a1.get("Contents"));
			for (String s : a3) {
				texts.add(TextSerializers.JSON.deserializeUnchecked(s));
			}
			a2.contents(texts).sendTo(player);
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

	@Override
	public List<String> getSuggestions(CommandSource source, String arguments, Location<World> targetPosition) throws CommandException {
		List<String> argumentsList = new ArrayList<String>();
		if (arguments.length() == 0) {
			argumentsList.add("reload");
			argumentsList.add("send");
			argumentsList.add("sendto");
		} else if (arguments.contains("sendto ")) {
			for (Player player : Sponge.getServer().getOnlinePlayers()) {
				argumentsList.add(player.getName());
				if (arguments.contains("sendto "+player.getName())) {
					argumentsList.clear();
					argumentsList.addAll(BaiConfig.CMCSet);
				}
			}
		} else if (arguments.contains("send ")) {
			argumentsList.addAll(BaiConfig.CMCSet);
		}
		return argumentsList;
	}

	@Override
	public boolean testPermission(CommandSource source) {
		return source.hasPermission("CustomMessage.plugin");
	}

	@Override
	public Optional<Text> getShortDescription(CommandSource source) {
		return Optional.of(Text.of());
	}

	@Override
	public Optional<Text> getHelp(CommandSource source) {
		return Optional.of(Text.of());
	}

	@Override
	public Text getUsage(CommandSource source) {
		return Text.of();
	}

}
