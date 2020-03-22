package com.jsonmack.mcplugins.one_versus_one.service;

import com.jsonmack.mcplugins.one_versus_one.request.OneVersusOneRequest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Jason MK on 2020-03-18 at 12:59 p.m.
 */
public class OneVersusOneRequestService {

    private final Set<OneVersusOneRequest> requests = new HashSet<>();

    public void add(OneVersusOneRequest request) {
        requests.add(request);
    }

    public boolean contains(UUID source, UUID target) {
        return requests.stream().anyMatch(request -> request.getSource().equals(source) && request.getTarget().equals(target));
    }

}
