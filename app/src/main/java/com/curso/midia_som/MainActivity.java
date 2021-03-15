package com.curso.midia_som;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configurar uma musica a ser executada
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);

    }

    private void inicializarSeekBar(){
        seekVolume = findViewById(R.id.seekVolume);

        //configurar o audio mananger "qual é o volume atual e volume maximo que o usuario pode usar
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //recuperando o servico de audio

        //recuperar os valores de volume maximo e o volume atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //configurar os valores maximos para o SeekBar
        seekVolume.setMax(volumeMaximo);

        //configurar o progresso atual do SeekBar
        seekVolume.setProgress(volumeAtual);

        //permitir que o usuario consiga arrastar a seekBar para aumentar ou diminuir o volume
        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void executarSom(View view){
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public void pausarMusica(View view){
        if (mediaPlayer.isPlaying()){ // se a musica estiver tocando
            mediaPlayer.pause();
        }
    }

    public void pararMusica(View view){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop(); // parando a musica ele fecha o chamado da musica
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste); // chamando a execucao da musica novamente
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release(); // libera recursos de media que esta executando com a classe mediaPlayer
            mediaPlayer = null;
        }
    }

    //toda vez que o usuario sair do app a musica vai pausar
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()){ // se a musica estiver tocando
            mediaPlayer.pause();
        }
    }
}
