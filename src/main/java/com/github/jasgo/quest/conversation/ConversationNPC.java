package com.github.jasgo.quest.conversation;

import net.citizensnpcs.api.npc.NPC;

import java.util.List;
import java.util.UUID;

public class ConversationNPC {
    private Conversation conversation;
    private UUID npc;

    public ConversationNPC(Conversation conversation, UUID npc) {
        this.conversation = conversation;
        this.npc = npc;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public UUID getNpc() {
        return npc;
    }

    public void setNpc(UUID npc) {
        this.npc = npc;
    }
}
