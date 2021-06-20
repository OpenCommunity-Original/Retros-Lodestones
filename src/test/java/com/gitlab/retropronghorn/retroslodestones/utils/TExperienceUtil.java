package com.gitlab.retropronghorn.retroslodestones.utils;

import static org.mockito.Mockito.when;

import org.bukkit.entity.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.junit.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { Player.class })
public class TExperienceUtil {

    @Test
    public void testHasEnoughExperience() {
        Player mockPlayer = PowerMockito.mock(Player.class);
        float exp = 17;
        when(mockPlayer.getLevel()).thenReturn(1);
        when(mockPlayer.getExpToLevel()).thenReturn(17);
        when(mockPlayer.getExp()).thenReturn(exp);

        ExperienceUtil experienceUtil = new ExperienceUtil(null, mockPlayer);

        // Check that when the player has enouch experience it returns true
        Assert.assertTrue(experienceUtil.hasEnoughExperience(17.0));
        // Check that when the player doesn't have enough expereince it returns false
        Assert.assertFalse(experienceUtil.hasEnoughExperience(1055.0));
    }
}