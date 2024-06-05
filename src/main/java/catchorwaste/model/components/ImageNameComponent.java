package catchorwaste.model.components;

import com.almasb.fxgl.entity.component.Component;

public class ImageNameComponent extends Component {
    private String imageName;

    public ImageNameComponent(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}

