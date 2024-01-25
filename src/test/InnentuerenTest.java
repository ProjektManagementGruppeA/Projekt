package test;
import business.export.CsvFile;
import business.kundeSonderwunsch.*;
import business.sonderwunsch.*;
import gui.innentueren.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class InnentuerenTest {
private InnentuerenControl innentuerenControl;
private List<Sonderwunsch>sonderwuensche;
@Mock
InnentuerenView innentuerenView;

@Mock
KundeSonderwunschModel kundeSonderwunschModel;

@Mock
SonderwunschModel sonderwunschModel;
	@Before
	public void setUp() throws Exception {
	innentuerenControl= mock(InnentuerenControl.class);
	 sonderwuensche = new ArrayList<Sonderwunsch>();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testleseInnentuerenSonderwuenschePreise() {
		//Arrange
		sonderwuensche= Arrays.asList(
	            new Sonderwunsch(new ObjectId(), "Beschreibung 1", 100),
	            new Sonderwunsch(new ObjectId(), "Beschreibung 2", 200),
	            new Sonderwunsch(new ObjectId(), "Beschreibung 3", 300)
	        );

		//Act
		 int[] preise = innentuerenControl.leseInnentuerenSonderwuenschePreise();
		 
		 //verify
        assertArrayEquals(new int[]{100, 200, 300}, preise);

	}
	
	 @Test
	   public void testSpeichereToCsv() throws IOException {
	        // Arrange
	        File mockFile = mock(File.class); 
	        int[] input = {100, 200, 300, 600}; 
	        CsvFile mockCsvFile = mock(CsvFile.class);
	       
	        //act
	        innentuerenControl.speichereToCsv(mockFile, input);

	        //verify
	        verify(mockCsvFile).export();
	    }
	 
	 @Test
	  public   void testSpeichereSonderwuensche() throws Exception {
	        int[] anzahlarray = {1, 1, 1};
	        List<KundeSonderwunsch> kundeSonderwuensche =  Arrays.asList(
		            new KundeSonderwunsch(new ObjectId(), new ObjectId(), 1)
		        );
	        List<Sonderwunsch> sonderwuensche =  Arrays.asList(
		            new Sonderwunsch(new ObjectId(), "Beschreibung 1", 100),
		            new Sonderwunsch(new ObjectId(), "Beschreibung 2", 200),
		            new Sonderwunsch(new ObjectId(), "Beschreibung 3", 300)
		        );

	        // Mocking des Verhaltens von leseInnentuerenKundenSonderwuensche und leseInnentuerenSonderwuensche
	       when(kundeSonderwunschModel.getKundeSonderwuenscheByKategorie(new ObjectId(), "Innentüren")).thenReturn(kundeSonderwuensche);
	      when(sonderwunschModel.getSonderwunschByKategorie("Innentüren")).thenReturn(sonderwuensche); 
	        
	        when(InnentuerenValidierung.validiereGlasKlar(1)).thenReturn(true);
	        when(InnentuerenValidierung.validiereGlasMilch(1)).thenReturn(true);
	        when(InnentuerenValidierung.validiereGarage(1)).thenReturn(true);

	        innentuerenControl.speichereSonderwuensche(anzahlarray);

	        verify(innentuerenView, never()).zeigeFehlermeldung(anyString(), anyString());

	        verify(kundeSonderwunschModel, times(3)).addKundeSonderwunsch(any(), any(), anyInt()); 
	    }

}
