package com.minicliente;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class MiniClienteMod implements ClientModInitializer {

    private static int fotogramas = 0;
    private static int fpsActuales = 0;
    private static long ultimaMedicion = System.currentTimeMillis();

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(MiniClienteMod::dibujarHud);
    }

    private static void dibujarHud(DrawContext contexto, float tickDelta) {
        MinecraftClient cliente = MinecraftClient.getInstance();

        fotogramas++;
        long ahora = System.currentTimeMillis();
        if (ahora - ultimaMedicion >= 1000) {
            fpsActuales = fotogramas;
            fotogramas = 0;
            ultimaMedicion = ahora;
        }

        if (cliente.player == null || cliente.options.debugEnabled) {
            return;
        }

        String coords = String.format("XYZ: %.0f / %.0f / %.0f",
                cliente.player.getX(), cliente.player.getY(), cliente.player.getZ());
        String direccion = "Mirando: " + cliente.player.getHorizontalFacing().asString();

        String[] lineas = { "FPS: " + fpsActuales, coords, direccion };
        int y = 4;
        for (String linea : lineas) {
            contexto.drawTextWithShadow(cliente.textRenderer, linea, 4, y, 0xFFFFFF);
            y += 10;
        }
    }
}
