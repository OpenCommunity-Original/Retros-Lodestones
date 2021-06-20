package com.gitlab.retropronghorn.retroslodestones.utils;

import static org.mockito.Mockito.when;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.junit.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { Location.class, World.class, LocationUtil.class })
public class TLocationUtil {

    @Test
    public void testToString() {
        String testLocationString = "mockworld, 0, 64, 1";
        // Mock World to return name
        World mockWorld = PowerMockito.mock(World.class);
        when(mockWorld.getName()).thenReturn("mockworld");
        // Mock Location to return coords & world
        Location mockLocation = PowerMockito.mock(Location.class);        
        when(mockLocation.getWorld()).thenReturn(mockWorld);
        when(mockLocation.getBlockX()).thenReturn(0);
        when(mockLocation.getBlockY()).thenReturn(64);
        when(mockLocation.getBlockZ()).thenReturn(1);
        
        // Build our expected string
        String locationString = LocationUtil.toString(mockLocation);

        Assert.assertTrue(locationString.equals(testLocationString));
    }

    /*
    @Test
    public void testFromString() {
        String testLocationString = "normal, 0, 64, 1";
        // Mock World to return name
        World mockWorld = PowerMockito.mock(World.class);
        when(mockWorld.getName()).thenReturn("normal");
        // Mock Location to return coords & world
        Location mockLocation = PowerMockito.mock(Location.class);        
        when(mockLocation.getWorld()).thenReturn(mockWorld);
        when(mockLocation.getBlockX()).thenReturn(0);
        when(mockLocation.getBlockY()).thenReturn(64);
        when(mockLocation.getBlockZ()).thenReturn(1);
        // Mock bukkit to return our mockworld
        LocationUtil mockLocationUtil = PowerMockito.mock(LocationUtil.class);
        when(mockLocationUtil.getWorld("normal")).thenReturn(mockWorld);
        // Build our expected location
        Location locationInstance = mockLocationUtil.fromString(testLocationString);

        Assert.assertSame(mockLocation, locationInstance);
    }
    */
}