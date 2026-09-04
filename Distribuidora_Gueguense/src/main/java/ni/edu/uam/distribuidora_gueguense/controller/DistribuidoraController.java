package ni.edu.uam.distribuidora_gueguense.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.distribuidora_gueguense.models.Producto;

import java.util.Optional;

public class DistribuidoraController {

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> cbCategoria;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtExistencia;
    @FXML
    private Label lblResultado;

    @FXML
    private TableView<Producto> tbProductos;
    @FXML
    private TableColumn<Producto, String> colCodigo;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, String> colCategoria;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, Integer> colExistencia;

    private ObservableList<Producto> productos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbCategoria.getItems().addAll(
                "Alimentos", "Bebidas", "Limpieza", "Hogar", "Otros"
        );

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));

        tbProductos.setItems(productos);
    }

    @FXML
    private void nuevo() {
        limpiarCampos();
        lblResultado.setText("Nuevo producto");
    }

    @FXML
    private void limpiar() {
        limpiarCampos();
        lblResultado.setText("Campos limpiados");
    }

    @FXML
    private void guardar() {
        if (camposVacios()) {
            lblResultado.setText("Complete todos los campos");
            return;
        }

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            int existencia = Integer.parseInt(txtExistencia.getText());

            Producto producto = new Producto(
                    txtCodigo.getText(),
                    txtNombre.getText(),
                    cbCategoria.getValue(),
                    precio,
                    existencia
            );

            productos.add(producto);
            lblResultado.setText("Producto guardado correctamente");
            limpiarCampos();

        } catch (NumberFormatException e) {
            lblResultado.setText("Precio o existencia no válidos");
        }
    }

    @FXML
    private void editar() {
        Producto producto = tbProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            alertaSeleccion();
            return;
        }

        if (camposVacios()) {
            txtCodigo.setText(producto.getCodigo());
            txtNombre.setText(producto.getNombre());
            cbCategoria.setValue(producto.getCategoria());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtExistencia.setText(String.valueOf(producto.getExistencia()));

            lblResultado.setText("Modifique los datos y vuelva a presionar Editar");
            return;
        }

        try {
            producto.setCodigo(txtCodigo.getText());
            producto.setNombre(txtNombre.getText());
            producto.setCategoria(cbCategoria.getValue());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setExistencia(Integer.parseInt(txtExistencia.getText()));

            tbProductos.refresh();
            limpiarCampos();
            lblResultado.setText("Producto editado correctamente");

        } catch (NumberFormatException e) {
            lblResultado.setText("Precio o existencia no válidos");
        }
    }

    @FXML
    private void eliminar() {
        Producto producto = tbProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            alertaSeleccion();
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar producto");
        alerta.setHeaderText("¿Desea eliminar este producto?");
        alerta.setContentText(producto.getNombre());

        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            productos.remove(producto);
            lblResultado.setText("Producto eliminado");
        }
    }

    @FXML
    private void verDetalle() {
        Producto producto = tbProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            alertaSeleccion();
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Detalle del producto");
        alerta.setHeaderText(producto.getNombre());
        alerta.setContentText(
                "Código: " + producto.getCodigo() +
                        "\nNombre: " + producto.getNombre() +
                        "\nCategoría: " + producto.getCategoria() +
                        "\nPrecio: C$ " + producto.getPrecio() +
                        "\nExistencia: " + producto.getExistencia()
        );
        alerta.showAndWait();
    }

    @FXML
    private void salir() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Salir");
        alerta.setHeaderText("¿Desea salir de la aplicación?");

        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    @FXML
    private void acercaDe() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Acerca de");
        alerta.setHeaderText("Distribuidora El Güegüense");
        alerta.setContentText(
                "Aplicación para administrar productos.\nAutor: Adolfo Ramírez"
        );
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        cbCategoria.setValue(null);
        txtPrecio.clear();
        txtExistencia.clear();
    }

    private boolean camposVacios() {
        return txtCodigo.getText().isBlank() ||
                txtNombre.getText().isBlank() ||
                cbCategoria.getValue() == null ||
                txtPrecio.getText().isBlank() ||
                txtExistencia.getText().isBlank();
    }

    private void alertaSeleccion() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Producto");
        alerta.setHeaderText(null);
        alerta.setContentText("Seleccione un producto.");
        alerta.showAndWait();
    }
}