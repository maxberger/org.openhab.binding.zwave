/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.zwave.test.internal.protocol.commandclass;

import org.junit.Test;
import org.openhab.binding.zwave.internal.protocol.SerialMessage;
import org.openhab.binding.zwave.internal.protocol.commandclass.ZWaveClimateControlScheduleCommandClass;
import org.openhab.binding.zwave.internal.protocol.commandclass.ZWaveClimateControlScheduleCommandClass.OverrideType;
import org.openhab.binding.zwave.internal.protocol.commandclass.ZWaveClimateControlScheduleCommandClass.ScheduleState;
import org.openhab.binding.zwave.internal.protocol.commandclass.ZWaveClimateControlScheduleCommandClass.ScheduleStateState;
import org.openhab.binding.zwave.internal.protocol.commandclass.ZWaveCommandClass.CommandClass;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test cases for {@link ZWaveClimateControlScheduleCommandClass}.
 *
 * @author Max Berger
 */
public class ZWaveClimateControlScheduleCommandClassTest extends ZWaveCommandClassTest {

    @Test
    public void getScheduleStateFor() {
        ZWaveClimateControlScheduleCommandClass cls = (ZWaveClimateControlScheduleCommandClass) getCommandClass(CommandClass.CLIMATE_CONTROL_SCHEDULE);

        assertEquals(new ScheduleState(ScheduleStateState.SETBACK, -128), cls.getScheduleStateFor((byte)0x80));
        assertEquals(new ScheduleState(ScheduleStateState.SETBACK, -1  ), cls.getScheduleStateFor((byte)0xFF));
        assertEquals(new ScheduleState(ScheduleStateState.SETBACK, 0   ), cls.getScheduleStateFor((byte)0x00));
        assertEquals(new ScheduleState(ScheduleStateState.SETBACK, 1   ), cls.getScheduleStateFor((byte)0x01));
        assertEquals(new ScheduleState(ScheduleStateState.SETBACK,  120), cls.getScheduleStateFor((byte)0x78));
        assertEquals(new ScheduleState(ScheduleStateState.FROST_PROTECTION, 0), cls.getScheduleStateFor((byte)0x79));
        assertEquals(new ScheduleState(ScheduleStateState.ENERGY_SAVING, 0), cls.getScheduleStateFor((byte)0x7A));
        assertEquals(new ScheduleState(ScheduleStateState.RESERVED, 0), cls.getScheduleStateFor((byte)0x7B));
        assertEquals(new ScheduleState(ScheduleStateState.UNUSED, 0), cls.getScheduleStateFor((byte)0x7F));
    }

    @Test
    public void getOverrideTypeFor() {
        ZWaveClimateControlScheduleCommandClass cls = (ZWaveClimateControlScheduleCommandClass) getCommandClass(CommandClass.CLIMATE_CONTROL_SCHEDULE);
        assertEquals(OverrideType.NO_OVERRIDE, cls.getOverrideTypeFor(0));
        assertEquals(OverrideType.TEMPORARY_OVERRIDE, cls.getOverrideTypeFor(1));
        assertEquals(OverrideType.PERMANENT_OVERRIDE, cls.getOverrideTypeFor(2));
        assertEquals(OverrideType.RESERVED, cls.getOverrideTypeFor(3));
    }

    @Test
    public void getScheduleChangedReportMessage() {
        ZWaveClimateControlScheduleCommandClass cls = (ZWaveClimateControlScheduleCommandClass) getCommandClass(CommandClass.CLIMATE_CONTROL_SCHEDULE);

        byte[] expectedResponse = { 99, 4, 70, 5, 0 };

        SerialMessage msg = cls.getScheduleChangedReportMessage((byte)0);

        assertArrayEquals(expectedResponse, msg.getMessagePayload());
    }

}
