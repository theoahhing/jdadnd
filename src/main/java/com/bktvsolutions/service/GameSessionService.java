/**
    GameSessionService manages all active game sessions across guilds.

    Each guild can have its own independent session.
*/

package com.bktvsolutions.service;

import com.bktvsolutions.model.GameSession;

import java.util.HashMap;
import java.util.Map;

public class GameSessionService {

    private final Map<Long, GameSession> sessions;

    public GameSessionService() {
        this.sessions = new HashMap<>();
    }

    /**
        Creates a new session for a guild.

        @param guildId discord guild id
    */

    public GameSession createSession(long guildId) {
        GameSession session = new GameSession();
        sessions.put(guildId, session);
        return session;
    }

    /**
        Retrieves an existing session.

        @param guildId discord guild id
    */

    public GameSession getSession(long guildId) {
        return sessions.get(guildId);
    }

    /**
        Removes a session.

        @param guildId discord guild id
    */

    public void removeSession(long guildId) {
        sessions.remove(guildId);
    }

    public boolean hasSession(long guildId) {
        return sessions.containsKey(guildId);
    }
}