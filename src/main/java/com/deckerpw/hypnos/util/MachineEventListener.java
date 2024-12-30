package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.machine.Machine;

import java.util.EventListener;

public interface MachineEventListener extends EventListener {

    void actionPerformed(Machine machine);

}
