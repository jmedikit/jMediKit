package org.jmedikit.lib.io;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import org.jmedikit.lib.core.DicomObject;
import org.jmedikit.lib.core.DicomTreeRepository;

public class DicomImporter extends Job{
	
	//@Inject
	//IEventBroker broker;
	
	File importLocation;
	
	private int filenumber;
	
	IProgressMonitor monitor;
	
	private DicomTreeRepository tree;
	
	public DicomImporter(String jobName, File f){
		super(jobName);
		importLocation = f;
		filenumber = 0;

		tree = new DicomTreeRepository();
	}
	
	private void countFiles(File f){
		if(f.isDirectory()){
			File[] files = f.listFiles();
			for(int i = 0; i < files.length; i++){
				if(files[i].isDirectory()){
					countFiles(files[i]);
				}
				else{
					filenumber++;
				}
			}
		}
		else{
			filenumber++;
		}
	}
	
	public static DicomTreeRepository recursiveImport(File root){
		DicomTreeRepository repository = new DicomTreeRepository();
		silentImportFiles(root, repository);
		return repository;
	}
	
	private static void silentImportFiles(File root, DicomTreeRepository repository){
		if(root.isDirectory()){
			File[] files = root.listFiles();
			for(File f : files){
				if(f.isDirectory()){
					//root = f;
					silentImportFiles(f, repository);
				}
				else{
					silentImportDicomObject(f, repository);
				}
			}
		}
		else {
			silentImportDicomObject(root, repository);
		}
	}
	
	private static void silentImportDicomObject(File f, DicomTreeRepository repository){
		DicomObject obj = null;

		try {
			obj = new DicomObject(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(obj != null){
			repository.insert(obj);
		}
	}
	
	private void importDicomFiles(){
		if(importLocation.isDirectory()){
			File[] files = importLocation.listFiles();
			for(File f : files){
				if(f.isDirectory()){
					importLocation = f;
					importDicomFiles();
				}
				else{
					importDicomObject(f);
					monitor.worked(1);
				}
			}
		}
		else {
			importDicomObject(importLocation);
			monitor.worked(1);
		}
	}
	
	private void importDicomObject(File f){

		DicomObject obj = null;

		try {
			obj = new DicomObject(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(obj != null){
			tree.insert(obj);
		}
		
	}
	
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		countFiles(importLocation);
		
		this.monitor = monitor;
		this.monitor.beginTask(this.getName(), filenumber);
		
		this.importDicomFiles();
		//tree.walkDicomTreeRepository();
		this.monitor.done();

		return Status.OK_STATUS;
	}

	public DicomTreeRepository getTree() {
		return tree;
	}
}
