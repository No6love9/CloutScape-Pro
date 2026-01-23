package org.dreambot.api.script; public abstract class AbstractScript { public abstract int onLoop(); public void onStart() {} public void onExit() {} public void log(String msg) {} }
