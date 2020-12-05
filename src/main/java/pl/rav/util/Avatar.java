package pl.rav.util;

public enum Avatar {

    HEAVY_GUY ("src\\main\\resources\\pl\\rav\\graphics\\avatars\\humans\\humanAvatar01.png"),
    MEDIUM_GUY ("src\\main\\resources\\pl\\rav\\graphics\\avatars\\humans\\humanAvatar02.png"),
    LIGHT_GUY ("src\\main\\resources\\pl\\rav\\graphics\\avatars\\humans\\humanAvatar03.png"),

    HEAVY_MONSTER ("src\\main\\resources\\pl\\rav\\graphics\\avatars\\monsters\\monsterAvatar01.png"),
    MEDIUM_MONSTER ("src\\main\\resources\\pl\\rav\\graphics\\avatars\\monsters\\monsterAvatar02.png"),
    LIGHT_MONSTER ("src\\main\\resources\\pl\\rav\\graphics\\avatars\\monsters\\monsterAvatar03.png"),
    ;

    String resourcesPath;

    Avatar (String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public String getPath() {
        return resourcesPath;
    }

}
