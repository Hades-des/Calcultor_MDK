package com.example.calcultor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView resultField; // текстовое поле для вывода результата
    EditText numberField;   // поле для ввода числа
    TextView operationField;    // текстовое поле для вывода знака операции
    Double operand = null;  // операнд операции (тип данных по типу float)

    /*
    операнд - аргумент операции; данные в которые обрабатываются командой.
    Например: 3 + 6 = 9, где '+' - оператор сложения, а 3 и 6 - операнды
   */

    String lastOperation = "="; // последняя операция (строка)

    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        /*Метод onCreate() вызывается при создании или перезапуска активности.
        Система может запускать и останавливать текущие окна в зависимости от происходящих событий. 
        Внутри данного метода настраивают статический интерфейс активности.
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        // получаем все поля по id из activity_main.xml
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);
    }

    /*
    Так как при переходе от портретной ориентации к альбомной или наоборот мы можем потерять все
    введенные данные, то чтобы их не потерять, мы их сохраняем в методе onSaveInstanceState() и
    обратно получаем в методе onRestoreInstanceState().
    */

    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        /*
        Когда система завершает активность в принудительном порядке,
        чтобы освободить ресурсы для других приложений, пользователь может снова вызвать эту активность с сохранённым предыдущим состоянием. 
        Чтобы зафиксировать состояние активности перед её уничтожением, в классе активности необходимо реализовать метод onSaveinstancestate().
        */
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }
    
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        /*
        У метода onRestoreInstanceState() есть такой же параметр Bundle, как у onCreate(),
        и вы можете восстанавливать сохранённые значения из метода onSaveInstanceState(). 
        Во многих случаях это пример личных предпочтений, какой из двух методов использовать для восстановления данных.
        */
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }
    
    // обработка нажатия на числовую кнопку
    public void onNumberClick(View view){

        Button button = (Button)view;
        numberField.append(button.getText());

        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
        /*
        При нажатии на числовую кнопку будет вызываться метод onNumberClick,
         в котором добавляем введенную цифру или знак запятой к тексту в поле numberField:
         */
    }
    
    // обработка нажатия на кнопку операции
    public void onOperationClick(View view){

        Button button = (Button)view;
        String op = button.getText().toString(); //получение текста с кнопки
        String number = numberField.getText().toString();
        // если введенно что-нибудь
        if(number.length()>0){
            number = number.replace(',', '.');
            try{
                performOperation(Double.valueOf(number), op);
            }catch (NumberFormatException ex){
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
        /*
        Здесь получаем ранее введенное число и введенную операцию и передаем их в метод performOperation(). 
        Так как в метод передается не просто строка, а число Double, то нам надо преобразовать строку в чсло. 
        И поскольку теоретически могут быть введены нечисловые символы, то для отлова исключения, 
        которое может возникнуть при преобразовании используется конструкция try...catch.

        Кроме того, так как разделителем целой и дробной части в Double в java является точка, 
        то нам надо заменить запятую на точку, так как предполагается, что мы используем в качестве разделителя запятую.
        */
    }

    private void performOperation(Double number, String operation){
        
        /*
        А методе performOperation() выполняем собственно операцию.
        При вводе первой операции, когда операнд еще не установлен, мы просто устанавливаем операнд:
        (если операнд ранее не был установлен (при вводе самой первой операции))
        */
        if(operand ==null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch(lastOperation){ //через switch задаем нужные операции
                case "=":
                    operand =number;
                    break;
                case "/":
                    if(number==0){
                        operand =0.0;
                    }
                    else{
                        operand /=number;
                    }
                    break;
                case "*":
                    operand *=number;
                    break;
                case "+":
                    operand +=number;
                    break;
                case "-":
                    operand -=number;
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }

}
