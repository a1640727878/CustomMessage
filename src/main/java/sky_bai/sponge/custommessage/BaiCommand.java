package sky_bai.sponge.custommessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ninja.leaping.configurate.ConfigurationNode;

public class BaiCommand implements CommandCallable{

	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		if (arguments.startsWith("reload")) {
			CustomMessage.setConfig();
			source.sendMessage(Text.of("§l[CustomMessage]§r插件重载完成"));
			return CommandResult.success();
		} else if (arguments.startsWith("send")) {
			String mesNameString = arguments.replace("send ", "");
			if (!BaiConfig.mesName.contains(mesNameString)) {
				return CommandResult.empty();
			}
			BaiConfig.mes.get(mesNameString);
			ConfigurationNode a2 = BaiConfig.getConfig(BaiConfig.configPath).getNode(mesNameString);
			PaginationList.Builder a1 = PaginationList.builder().title(Text.of(a2.getNode("Title").getValue())).linesPerPage(a2.getNode("Page").getInt(0)+2).padding(Text.of(a2.getNode("Padding").getValue()));
			List<Text> texts = new ArrayList<Text>();
			for (String s : BaiConfig.mes.get(mesNameString)) {
				texts.add(TextSerializers.JSON.deserializeUnchecked(s));
				a1.contents(texts);
			}
			a1.sendTo(source);
		}
		return CommandResult.empty();
	}

	@Override
	public List<String> getSuggestions(CommandSource source, String arguments, Location<World> targetPosition) throws CommandException {
		List<String> argumentsList = new ArrayList<String>();
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
