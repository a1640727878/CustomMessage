package sky_bai.sponge.custommessage;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ConfigSerializable
public class CMObject {
    @Setting("Title")
    private String title = "";
    @Setting("Padding")
    private String padding = "=";
    @Setting("Title")
    private int page = 0;
    @Setting("Contents")
    private List<String> contents = Collections.emptyList();

    private transient PaginationList.Builder list;

    private CMObject() {
    }

    public CMObject(String title, String padding, int page, List<String> contents) {
        this.title = title;
        this.padding = padding;
        this.page = page;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getPadding() {
        return padding;
    }

    public int getPage() {
        return page;
    }

    public List<String> getContents() {
        return contents;
    }

    public void sendTo(MessageReceiver receiver) {
        if (list == null) {
            list = PaginationList.builder()
                    .title(Text.of(title))
                    .linesPerPage(page + 2)
                    .padding(Text.of(padding))
                    .contents(contents.stream().map(CustomMessage::deserializeToText).collect(Collectors.toList()));
        }
        list.sendTo(receiver);
    }
}
